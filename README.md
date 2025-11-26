# recipe-vault
RecipeVault is an Android app that acts as your personal digital cookbook — helping you store, retrieve, and manage your favorite recipes in one place.
Designed with a focus on clean architecture and an intuitive UI, RecipeVault makes it effortless for cooking enthusiasts to organize their culinary creations and discover new dishes.

✨ Features
------------------------
📚 Store, Add, and Retrieve Recipes
Manage all your recipes directly within the app - including the ability to add new recipes from the app.

🗂️ Local Database Integration
On first launch, static JSON data is parsed and stored into the local Room database.

🍽️ Render Recipes from Database
Recipes are dynamically retrieved from the local database and displayed in the app’s UI.

🧩 MVVM + Clean Architecture
Built using a modular and maintainable architecture pattern.

🎨 XML-based UI
UI screens and layouts are implemented using XML views for flexibility and clarity.

🚀 Current Version
------------------------
v0.1 : Static Data to Local DB
- Static JSON data is loaded on app startup
- Recipes are stored in the Room database
- Data is rendered dynamically from the DB
- Includes basic Recipe List and Recipe Detail screens
- Add Recipes directly from the app UI: Users can now create and save their own recipes within the app.
- Share Recipe : Users can share recipe detail cards as PNG images through social media or messaging apps, making it easy to share their favorite dishes.

Next milestone: Enable users to add recipes directly from the app UI
