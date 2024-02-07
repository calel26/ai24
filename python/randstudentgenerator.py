import random

students = [
    "Dawson", "Lawson", "Avery", "Saige",
    "Kate", "Kassidy", "Alec", "David"
]


def rem_rand():
    global students
    i = random.randint(0, len(students) - 1)
    s = students[i]
    students.remove(s)
    return s


while True:
    print("Press `1` to get a random student and `2` to exit")
    selection = int(input("=> "))
    if selection == 1:
        if len(students) == 0:
            print("<! No more students")
            break
        stu = rem_rand()
        print("<= " + stu)
    elif selection == 2:
        print("bye bye")
        break
