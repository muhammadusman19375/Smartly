# What has been done ?
I have created a splash screen. After showing one second delay on splash then intent from splash to main activity. Main activity contains five fragment.
# Home Fragment:
Home Fragment containt quick mode, history buttons and the spinners for allowing user to select category, difficulty level and type. After these user can go to quiz fragment by clicking on start button.
# Score fragment:
On score framgment user can see its earned and total score. By clicking on button user navigate to home fragment.
# Quiz fragment:
Quiz fragment contain question four options, user earned score and difficulty level.By clicking on next button user can navigate of next question and user question data (question, answer, category and earned score) will saved in room database. After answering all questions user will navigate to score fragment.
# Quick mode fragment:
Quick mode fragment is same like home fragment but additionally user have 3 lifes and user have 5 seconds to answer question. if user not answer question within five second or answer incorrectly user will lost one life. On losing three lifes game will over and user will naviagte to score fragment where can see its earned and total score.
# History fragment:
History fragment shows all the records that user has answered the questions that i have saved on quiz fragment.

# What third you used to do what ?
I use navigation component for navigation with app,
Retrofit for networking, api calling,
Hilt for dependency injection,
Coroutines for asynchronous programming,
Live for observing data live in fragments or activities,
Room for saving data in local database.
Lottie animation for showing fun icon in qick mode fragment.

# Any known bugs or issues in the app?
According to mine there is no bug within application.

# Any improvements you see that can be made in the app?
We can also add hint, skip options, offline mode, leaderboard for showing top scorer.
