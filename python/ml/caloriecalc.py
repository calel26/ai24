import numpy as np
import csv
full_list = []

with open("nutrients.csv", 'r') as file:
    csvreader = csv.reader(file)
    for row in csvreader:
        full_list.append(row)

result = np.array(full_list)

tr = result.T
food = tr[0]
categories = np.copy(tr[8])

for i in range(len(categories)):
    c = categories[i]
    if "Vegetables" in c:
        c = "Vegetables"
    elif "Fruits" in c:
        c = "Fruits"
    categories[i] = c


macros = np.zeros([len(result), 3])
cals = np.zeros([len(result), 3])

for i in range(len(result)):
    cals[i][0] = 4  # carbs
    cals[i][1] = 4  # protein
    cals[i][2] = 9  # fat


for i in range(len(result)):
    for j in range(1, len(result[i])):
        k = result[i][j]
        if not k.isnumeric():
            result[i][j] = 0

for i in range(len(result)):
    macros[i][0] = result[i][3]
    macros[i][1] = result[i][7]
    macros[i][2] = result[i][4]

tot_cals = macros * cals
ta = False


def get_food(name):
    itemindex = np.where(food == name)
    return sum(tot_cals[itemindex][0])


def get_foods():
    return food


def get_category(name):
    itemindex = np.where(food == name)
    return categories[itemindex[0][0]]


def get_categories():
    return categories


if __name__ == "__main__":
    while True:
        print(food)
        if ta:
            print("try again")
        inp = input("enter food: ")

        itemindex = np.where(food == inp)

        if itemindex[0].size == 0:
            ta = True
            continue

        print(sum(tot_cals[itemindex][0]))
        print(get_category(inp))
        break
