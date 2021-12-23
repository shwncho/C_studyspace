# ProblemSolvingPattern
For TermProject code 

General Guideline

- You must write the first 4 problem solving steps in detail, plus the working source code (step 5).
- The overall structure of the entire program in terms of functions 
is very important.
- This is a team project.
- After defining the overall structure of the program, the team 
should assign different functions to different members of the 
team.
- Each team member should test and document his/her own 
functions.
- The team should put the pieces together, and do the testing of 
the entire program together, and update the documentation 
together.


Submission Guideline
- When submitting the project, each team member must sign 
off on percentage contribution and the work done.
- The submission should include the problem solving step 
writeup, source code, result screen capture, and the 
individual contributions signoff – all in a single ZIP file. 
- The results have to be reproduceable by a Test PC.
- Never manipulate the result, otherwise you will get ZERO point


Term Project Specification:

(1) Setup
-** practice file I/O, struct array, basic data structures
-Read a text file provided (conference registrations
data).
-Store the data in a struct array.
-Store the data in a linked list.

Registration Data

(30 records -- including some “Choi” , and some
“Gachon University” in name & organization fields)
-tag# (registration number – unique integer)
-date registered (yyyy-mm-dd)
-fee-paid (“yes” or “no”)
-name (char[25])
-age (integer)
-organization (company or university; char[35])
-job (student, professor, staff, executive, engineer,
marketer; char[15])

Term Project Specification:

(2) Search, Reorganize
- Search for “Choi” (if found, print all information about the persons)
- in the array (Mark P1-1 in code, Print result P1-1.png)
- In the linked list (Mark P1-2 in code, Print result P1-2.png)
- Search for all from Gachon University (if found, print all information about the
persons).
- in the array (Mark P2-1 in code, Print result P2-1.png)
- in the linked list (Mark P2-2 in code, Print result P2-2.png)
- Sort the data in the array in tag# order (Mark P3-1 in code, Print result P3-1.png)
- Create a linked list using the sorted data. (Mark P4-1 in code, Print result P4-1.pn g)
- Sort the data in the array in age group order (using selection sort – self-study)
- ** “age group” means 10 (10-19), 20 (20-29), 30 (30-39), ….
- Write the sorted data to a text file. (Mark P5-1 in code, submit a text file P5-1.txt)

Term Project Specification:

(2) Update
-All “Choi”s canceled registration. Remove the data from
-the array (fill the memory) (Mark P6-1 in code, Print result P6-1.png)
-the linked list (Mark P6-2 in code, Print result P6-2.png)
-One “Paik” registered late.
-tag#/2021-05-05/yes/Gildong Paik/35/Gachon University/Student.
-Give Paik's tag# in consideration of the tag# of the registere
d persons. Add the data to
-the array (in the sorted order; shift all affected data)
- (Mark P7-1 in code, Print result P7-1.png)
-the linked list (in the right sorted order)
- (Mark P7-2 in code, Print result P7-2.png)

Term Project Specification:

(2) Update
- Compute the checksum of the Array List – (1)
- Mark P8-1 in the code and Print result P8-1.png
- Compute the checksum of the Linked List – (2)
- Mark P8-2 in the code and Print result P8-2.png
- Compare the checksums against each other (1)==(2)
- Confirm that the two data are the same. - Mark P8-3 in the code and Print result P8-3.png
