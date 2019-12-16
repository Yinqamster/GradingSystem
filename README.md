# Gradebook - A Grading System

## Group #5
 - Jiatong Hao, U42186937
 - Jun Li, U73344054
 - Jiaqian Sun, U48621793
 - Qi Yin, U31787103

## How to run the code
1. Add all jars under `lib/` as project dependency. For your convenience, please use an IDE.
2. Run `src/view/ViewTest.java`.

### Instructions (core functions)

#### Login
1. Click on `Login` (username and password are unnecessary)

#### Course List
1. Open a course:
   - Select one course from the list in the left
   - Click on `Open`
2. Delete a course:
   - Select one course from the list in the left
   - Click on `Delete`, then a message box shows
   - Choose `Yes` or `No` in the message box
   - If `Yes`, then the selected course will be deleted
3. Add a coruse:
   - Click on `Add`, then a new `Add a Course` frame shows
   - Enter all the textfields with `*`(you can choose to start from an existing breakdown or import students from an Excel file)
   - Click on `Save`, then a new course added
4. Show courses in history:
   - Click on `History`, then all courses show in the list
   - `Delete` and `Add` will be disabled

#### Main Frame
1. Click on `Add Student`, a new frame shows
   - Enter all the textfields with `*`
   - Click on `Save`, a new student is added
2. Add breakdown
   - Select a node and right click on it
   - Click on `Add`, a new frame shows
   - enter new breakdown settings
   - Click on `Save`, a new breakdown saved
3. Edit breakdown
   - Select a node, its setting will show at the bottom
   - Change its setting
   - Click on `Save`, then its setting saved
4. Edit letter rule
   - Select a letter rule, its setting will show at the bottom
   - Change its setting
   - Click on `Save`, then its setting saved
5. Edit grades
   - Double click on a cell to edit it
   - Click on `Save`, then all grades saved
6. Calculate grades
   - Click on `Calculate Grade`, all grades will be calculated by the setting of breakdown and letter rules
7. Statistics
   - Click on `Statistics`, a new frame shows
   - Choose an assignment/exam, its mean, median, and standard deviation will show in the lower part.
8. Add comment
   - Right click on a grade and click on `Add/Edit Comment`, a new frame shows
   - Enter comment for this grade, click on `Save`, the comment saved
   - Any grade who has a comment will be highlighted