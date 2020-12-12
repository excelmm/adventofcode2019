package main

import (
	"bufio"
	"fmt"
	"io/ioutil"
	"os"
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
	suma := 0
	sumb := 0
	for scanner.Scan() {
		x, err := strconv.Atoi(scanner.Text())
		if err != nil {
			os.Exit(0)
		}
		suma += x/3 - 2
		sumb += fuelsum(x)
	}
	fmt.Println(suma)
	fmt.Println(sumb)
}

func fuelsum(i int) int {
	new := i/3 - 2
	if (new) <= 0 {
		return 0
	}
	return new + fuelsum(new)
}
