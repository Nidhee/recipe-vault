package com.nidhi.recipevault.ui.fragment

import android.content.ClipData
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.nidhi.recipevault.R
import com.nidhi.recipevault.ui.adapter.RecipePagerAdapter
import com.nidhi.recipevault.utils.LogUtils
import com.nidhi.recipevault.utils.getDrawableIdByName
import com.nidhi.recipevault.databinding.RecipeDetailBinding
import com.nidhi.recipevault.domain.model.Recipe
import android.net.Uri
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewOutlineProvider
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.core.view.drawToBitmap
import androidx.lifecycle.Lifecycle
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nidhi.recipevault.databinding.LayoutRecipeSharePreviewBinding
import java.io.File
import java.io.FileOutputStream

class RecipeDetailFragment : Fragment() {
    private var _binding: RecipeDetailBinding? = null
    private val binding get() = _binding!!
    var recipe : Recipe? = null
    private var recipePagerAdapter : RecipePagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LogUtils.getTag(this::class.java), "RecipeDetailFragment class onViewCreated called")
        _binding = RecipeDetailBinding.bind(view)


        // Enable Back Button
        val toolbar : Toolbar = binding.recipeDetailToolBar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle Back Button Click
        binding.recipeDetailToolBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        recipe = arguments?.getParcelable("recipe")
        recipe?.let {
            setRecipeData(it)
        }
        setMenuProvider()
        initViews()
    }
    private fun initViews(){
        recipePagerAdapter = RecipePagerAdapter(this, recipe!!)
        binding.viewPager.adapter = recipePagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Ingredients" else "Method"

        }.attach()
    }
    private fun setMenuProvider() {
        // Add MenuProvider
        (activity as AppCompatActivity).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_recipe_detail, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_share -> {
                        shareRecipeImage()
                        true
                    }
                    android.R.id.home -> {
                        requireActivity().supportFragmentManager.popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun setRecipeData(recipe: Recipe) {
        // Load image - check if it's a URI or drawable resource name
        val imageSource = recipe.thumbnail?.let { thumbnail ->
            when {
                thumbnail.startsWith("content://") || thumbnail.startsWith("file://") -> {
                    // It's a URI, load it directly
                    Uri.parse(thumbnail)
                }
                else -> {
                    // It's a drawable resource name, get the resource ID
                    context?.getDrawableIdByName(thumbnail) ?: R.drawable.ic_default_thumbnail
                }
            }
        } ?: R.drawable.ic_default_thumbnail
        Glide.with(binding.recipeImage.context)
            .load(imageSource)
            .placeholder(R.drawable.ic_default_thumbnail)
            .into(binding.recipeImage)
        binding.recipeDetailCollapsingToolbar.title = recipe.name
        binding.recipeDescription.text = recipe.description
        binding.cookTime.text =  getString(R.string.cook_time, recipe.cookTimeMinutes.toString())
        binding.prepTime.text =  getString(R.string.prep_time, recipe.prepTimeMinutes.toString())
        binding.difficulty.text = recipe.difficultyLevel.toString()
        binding.cuisine.text = recipe.cuisine.toString()
        binding.mealType.text = recipe.mealType.toString()
    }
    /**
    * Builds an off-screen share card that mirrors the user-facing recipe details,
    * renders that card to a bitmap, saves it as a PNG in the cache directory, and
    * launches the Android share sheet with the generated image stream.
    */
    private fun shareRecipeImage() {
        val currentRecipe = recipe ?: return

        // 1. Inflate the custom share layout OFF-SCREEN
        val previewBinding = LayoutRecipeSharePreviewBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        // make sure the MaterialCardView clips its outline when rendered off-screen
        previewBinding.shareCard.apply {
            // ensure an outline exists (uses card radius)
            outlineProvider = ViewOutlineProvider.BACKGROUND
            clipToOutline = true
            setCardBackgroundColor(
                ContextCompat.getColor(context, R.color.colorPrimaryContainer)
            )
        }

        // 2. Bind basic text data
        previewBinding.shareRecipeTitle.text = currentRecipe.name
        val description = currentRecipe.description?.takeIf { it.isNotBlank() }
        previewBinding.shareRecipeDescription.apply {
            text = description.orEmpty()
            visibility = if (description == null) View.GONE else View.VISIBLE
        }

        // 3. Build INGREDIENTS text (simple line-by-line list)
        val ingredientLines = currentRecipe.ingredients.map { ingredient ->
            val qtyPart = buildString {
                ingredient.qty?.let { qty ->
                    append(formatQuantity(qty))
                    ingredient.maxQty?.let { max ->
                        if (max > 0) {
                            append("–")
                            append(formatQuantity(max))
                        }
                    }
                    append(" ")
                }
            }
            val unitPart = ingredient.unit?.toString()?.lowercase()?.replace("_", " ")
                ?.plus(" ") ?: ""
            qtyPart + unitPart + ingredient.item
        }

        val firstColumnCount = (ingredientLines.size + 1) / 2
        previewBinding.shareRecipeIngredientsColumnStart.text =
            ingredientLines.take(firstColumnCount).joinToString("\n")
        val secondColumn = ingredientLines.drop(firstColumnCount)
        previewBinding.shareRecipeIngredientsColumnEnd.apply {
            text = secondColumn.joinToString("\n")
            visibility = if (secondColumn.isEmpty()) View.GONE else View.VISIBLE
        }

        // 4. Build METHOD text (numbered steps)
        val methodText = currentRecipe.methodSteps
            .sortedBy { it.stepOrder }
            .mapIndexed { index, step ->
                "${index + 1}. ${step.stepDescription}"
            }
            .joinToString(separator = "\n")
        previewBinding.shareRecipeMethod.text = methodText

        // 5. Load image and continue as before
        val imageSource = resolveRecipeImage(currentRecipe.thumbnail)
        Glide.with(this)
            .asBitmap()
            .load(imageSource)
            .placeholder(R.drawable.ic_default_thumbnail)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    previewBinding.shareRecipeThumbnail.setImageBitmap(resource)

                    val rawBitmap = previewBinding.shareCard.renderToBitmap(targetWidthDp = 420)
                    val roundedBitmap = rawBitmap.withRoundedCorners(radiusDp = 24f, resources)

                    val cacheDir = File(requireContext().cacheDir, "recipes").apply { mkdirs() }
                    val outFile = File(cacheDir, "recipe_${currentRecipe.recipeId}.png")
                    if (outFile.exists()) outFile.delete()
                    FileOutputStream(outFile).use {
                        roundedBitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
                    }

                    // Get Uri via FileProvider
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        outFile
                    )

                    // Share only the image
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/png"
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        clipData = ClipData.newRawUri("recipe_image", uri)
                    }

                    val chooser = Intent.createChooser(shareIntent, getString(R.string.share_recipe)).apply {
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    startActivity(chooser)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    previewBinding.shareRecipeThumbnail.setImageDrawable(placeholder)
                }
            })
    }
    /**
     * Creates a new bitmap by drawing this bitmap into a rounded-rect mask.
     * Used so the exported share image matches the Material card’s rounded corners.
     */
    private fun Bitmap.withRoundedCorners(radiusDp: Float, resources: Resources): Bitmap {
        val radiusPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            radiusDp,
            resources.displayMetrics
        )
        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, radiusPx, radiusPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(this, 0f, 0f, paint)
        paint.xfermode = null
        return output
    }

    // Helper to avoid "1.0" style quantities
    private fun formatQuantity(qty: Double): String {
        return if (qty % 1.0 == 0.0) qty.toInt().toString() else qty.toString()
    }
    /**
     * Turns a recipe thumbnail reference (URI string or drawable name) into an object
     * Glide can consume when rendering the share preview image.
     */
    private fun resolveRecipeImage(thumbnail: String?): Any = when {
        thumbnail.isNullOrBlank() -> R.drawable.ic_default_thumbnail
        thumbnail.startsWith("content://") || thumbnail.startsWith("file://") -> Uri.parse(thumbnail)
        else -> context?.getDrawableIdByName(thumbnail) ?: R.drawable.ic_default_thumbnail
    }
    /**
     * Measures and lays out the view off-screen at the requested width, then
     * renders it into a bitmap. Used to snapshot the share card layout.
     */
    private fun View.renderToBitmap(targetWidthDp: Int = 420): Bitmap {
        val widthPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            targetWidthDp.toFloat(),
            resources.displayMetrics
        ).toInt()
        val widthSpec = View.MeasureSpec.makeMeasureSpec(widthPx, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        measure(widthSpec, heightSpec)
        layout(0, 0, measuredWidth, measuredHeight)
        return drawToBitmap(Bitmap.Config.ARGB_8888)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LogUtils.getTag(this::class.java), "RecipeDetailFragment class onDestroyView called")
        // show main activity tool bar
        _binding = null
    }
}