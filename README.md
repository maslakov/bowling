# Bowling emulator

### Console application

Reads game record from the input.
 1. Put >> `1 2 3 4 5 / X X X 0 10 1 1 X 1 2`
 as parameter and get calculated score back.

 2. Run the app without parameters and use interactive mode to emulate real bowling game step by step.
 Only integer numbers are allowed in this case.
 
### Game class
Allows to emulate bowling game or fully recalculate score based on game record.
Accepts both formats:
 - as integer number of pins knocked down by every ball
 - as mixed numbers and "/" and "X" signs for spare and strikes

Does input validation for game records using **GameHelper** class.
Calls methods of **GameController** class to proceed with the game.

### Game controller class

Keeps track of the game, storing all relevant data (pins knocked down and score) in **ScoreTable** object.

Calculates game score after each step using **ScoreCalculator** object.

Swithches to the next frame, once previous one gets overflown (all possible balls are thrown).

### ScoreTable

Virtual table, which holds all the data of the game.

Allows to get game score at every step and save score for particular frame.

Owns **Frame** business objects, which incapsulate all the logic of game behavior.

### Frame

Every frame can be in one of the following states:
 - NOTCOUNTABLE - when it is not clear yet how to calculate the score for the frame.
 - one of the final states: REGULAR, SPARE or STRIKE
 
Every state has some properties, which define how to enter into this state and how score for the frames in this state must be calculated.
Those properties are 
 - custom reward for the frame (with default rules is 10 for SPARE/STRIKE)
 - number of following balls, whose score must ba taken into calculateion for this frame
 - how many balls we must throw before to potentially reach this state
 
 Such a way of defining game rules allows in my opinion to customize the game and introduce new rules.
 Like *"SPARE frame is when you hit 7 pins in last 3 attempt, you get +100 plus a number of 3 next balls results to the score"*
 
