package main

import (
	"bufio"
	"fmt"
	"io/ioutil"
	"os"
	"regexp"
	"strconv"
	"strings"
)

func main() {

	input, err := ioutil.ReadFile("input.txt")
	if err != nil {
		os.Exit(1)
	}

	scanner := bufio.NewScanner(strings.NewReader(string(input)))
	scanner.Split(bufio.ScanWords)

	scanner.Scan()
	between := scanner.Text()

	if err != nil {
		os.Exit(1)
	}
	re := regexp.MustCompile("(\\d+)-(\\d+)")
	min, err := strconv.Atoi(re.FindAllStringSubmatch(between, -1)[0][1])
	max, err := strconv.Atoi(re.FindAllStringSubmatch(between, -1)[0][2])

	occurences := []int{}

	for i := 0; i < 10; i++ {
		occurences = append(occurences, 0)
	}

	counta := 0
	countb := 0
	for i := min; i <= max; i++ {

		for j := 0; j < 10; j++ {
			occurences[j] = 0
		}
		valida := 1
		validb := 1

		numstring := strconv.Itoa(i)
		for j := 0; j < len(numstring)-1; j++ {
			occurences[numstring[j]-48]++
			if numstring[j+1]-numstring[j] > 9 {
				valida = 0
				validb = 0
				break
			}
			if j == len(numstring)-2 {
				occurences[numstring[j+1]-48]++
			}
		}

		if valida == 0 || validb == 0 {
			continue
		}
		for j := 0; j < 10; j++ {
			valida = 0
			if occurences[j] > 1 {
				valida = 1
				break
			}
		}
		for j := 0; j < 10; j++ {
			validb = 0
			if occurences[j] == 2 {
				validb = 1
				break
			}
		}
		if valida == 1 {
			counta++
		}
		if validb == 1 {
			countb++
		}
	}
	fmt.Println(counta)
	fmt.Println(countb)
}
