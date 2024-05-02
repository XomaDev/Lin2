# Lin2

My algorithm that is based on "philosophy of breaking complex things into smaller pieces" to compare them at their base level.
<br> It also utilizes levenshtein distance to compare two data set similarity.
<br>

This project takes in a large data set of 28x28 images, then emits compiled comparable model files.<br>
Minst dataset is taken as a prime example in this repository, that contains a large 28x28 data of 0-9 numbers.

After compiling the Minst dataset into models, we use the compare feature to effectively mimic a small OCR. (Thus its also some kind of ML)

## For instance, a test image (i created):

![numbers.png](/doubletest%2Fnumbers.png)

Then it does pixel simplification and blob mapping (blue part is blob detection overlay):

![output.png](/doubletest%2Foutput.png) 

### OCR Result: `893106` (5/6)
Out of 6 characters in the image, it got 5 of them right.