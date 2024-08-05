# CPSC210: My Personal Project
## Prevent waste with Basil, a web-based application for cooks!
For my personal project, I wish to make a recipe-geared application. In the application, one can *select an array of ingredients and find recipes that incorporate those ingredients with further filtering.* For example, if a user has some eggplants that are starting to mold in the fridge, they can use this application to find recipes that incorporate the eggplant. What's useful and unique about this application is that you can *specify which ingredients you do not have* or *specify the maximum cook time alloted* -- in this way, the user can easily find ways to salvage forgotten ingredients in the fridge.

**Potential list of features:**
- Classification of recipes to certain ingredients and cooking time
- Filtering system based on user inputs
- Variety of recipes cited off the internet
- Visual aid when browsing recipes
- Ability to save recipes into an album

***Motivation:*** The audience for this application is anyone who is willing to follow a recipe -- whether they are an experienced homecook or an amateur in the kitchen. This application will be useful for anyone who has to make use of a specific array of ingredients with certain restrictions. I, for one, always find myself in such a position and thus procured interest in this concept. There were many times when I would buy an unfamiliar ingredient for a specific dish and have to throw the remaining bits out due to my lack of knowledge on its usage. Therefore, I hope this application would be useful for other homecooks like me who is overwhelmed with the limited filering on the wide selection of recipes available on the internet.

## User Stories
- "I want to add the **recipes** I find into different **collections** and personalize the title and thumbnail."
- "I want a preview (title, author, cook-time, picture, rating) of recipes that incorporate my specified ingredients on one page when I press "Search" (view list of recipes).
- "I want to add my own recipes onto the application's database through a form."
- "I want to be able to comment on different recipes on the application."
- "I want to be able to choose whether I would or would not recommend a recipe and view recommend rate on recipes."
- "I want to be able to view the most viewed recipes that specific week."
- "I want to be able to watch videos on the site if the recipe has a respective video tutorial."
- "I want to be able to save the personal recipes and collections that I have created on my own volition."
- "I want to be able to load the recipes and collections that I have saved previously from a file."

## Graphical User Interface
**Relevant User Stories:**
- "I want to add my own recipes onto the application's database through a form."
- "I want a preview (title, author, cook-time, picture, rating) of recipes that incorporate my specified ingredients on one page when I press "Search" (view list of recipes).
- "I want to be able to remove all the recipes I have inputted with one click."
- "I want to be able to view all the recipes in the database by clicking through a list."
- "I want to be able to save the personal recipes and collections that I have created on my own volition."
- "I want to be able to load the recipes and collections that I have saved previously from a file."

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by going to the "Create Personal Recipe" tab, inputting information, and pressing "Add to Database".
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by going to "Search Database" and entering a word in the text-field to filter added recipes.
- EXTRA ACTION: Remove all user recipes by clicking "Remove all user recipes" in the "View All Recipes" tab.
- You can locate my visual component by observing the space under "Load File" and "Save File" buttons in the main tab to view the display picture.
- You can save the state of my application by clicking "Save file" in main tab.
- You can reload the state of my application by clicking "Load file" in main tab.

## STEP-BY-STEP INSTRUCTIONS
1. Launch the application
2. Click "Create Personal Recipe", a background image is added here.
3. Input a custom title and author, and a cook-time greater than 0.
4. Press "Add to Database" and ensure the text shows "Success!". You have added a recipe to the database.
5. Go to the "View All Recipes" tab and click on your added recipes.
6. Go to "Search Database" tab. Filter out ingredients that have butter by entering "butter" into the Text Field.
7. Go to main and save your file.
8. Close the app.
9. Run the application and load the file in the main screen. All panels should have your custom recipe.
10. Go to "View All Recipes" and click "Remove all user recipes" to remove your submissions.