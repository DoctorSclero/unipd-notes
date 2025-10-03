matrix = [[1,2,3,4,5], [6,7,8,9,10], [11,12,13,14,15], [16,17,18,19,20], [21,22,23,24,25] ]
n = 5

for i in range(0, int(n/2)):
    for j in range(i+1, n-i):
        temp = matrix[j][n-j]
        matrix[j][n-j] = matrix[n-j][j]
        matrix[n-j][j] = matrix[j][n-j]

for i in range(0,n):
    for j in range(0,n):
        print(matrix[i][j], end="\t")
    print("\n")

