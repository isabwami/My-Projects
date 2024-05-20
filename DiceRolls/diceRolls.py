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

    array[result - 2] += 1

    arrayIndex += 1

print("Results: ")

arrayIndex = 0

while arrayIndex < 11:

    if array[arrayIndex] == 1:
        print(arrayIndex + 2, "was rolled", array[arrayIndex], "time")
    else:
        print(arrayIndex + 2, "was rolled", array[arrayIndex], "times")

    arrayIndex += 1