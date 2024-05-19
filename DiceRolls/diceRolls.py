import random
import numpy

numRolls = (int)(input("How many rolls? "))
print ("Simulating", numRolls, "dice rolls...")

array = []
arrayIndex = 0

while arrayIndex < 11:
    array.append(0)
    arrayIndex += 1

arrayIndex = 0

while arrayIndex < numRolls:
    
    num1 = random.randint(1,6)
    num2 = random.randint(1,6)

    result = num1 + num2

    if result == 2:
        array[0] += 1
    
    elif result == 3:
        array[1] += 1
    
    elif result == 4:
        array[2] += 1
    
    elif result == 5:
        array[3] += 1
    
    elif result == 6:
        array[4] += 1

    elif result == 7:
        array[5] += 1

    elif result == 8:
        array[6] += 1

    elif result == 9:
        array[7] += 1
    
    elif result == 10:
        array[8] += 1

    elif result == 11:
        array[9] += 1

    else:
        array[10] += 1

    arrayIndex += 1

print("Results: ")

arrayIndex = 0

while arrayIndex < 11:

    if array[arrayIndex] == 1:
        print(arrayIndex + 2, "was rolled", array[arrayIndex], "time")
    else:
        print(arrayIndex + 2, "was rolled", array[arrayIndex], "times")

    arrayIndex += 1