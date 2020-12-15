package main

import (
	"bufio"
	"fmt"
	"io/ioutil"
	"os"
	"regexp"
	"strings"
)

type planet struct {
	name      string
	orbits    string
	orbitedby string
}

func main() {

	input, err := ioutil.ReadFile("input.txt")
	if err != nil {
		os.Exit(1)
	}
	scanner := bufio.NewScanner(strings.NewReader(string(input)))
	scanner.Split(bufio.ScanWords)

	var planets []planet

	for scanner.Scan() {
		x := scanner.Text()
		re := regexp.MustCompile(`(\w+)\)(\w+)`)
		group := re.FindAllStringSubmatch(x, -1)
		var orbits string = group[0][1]
		var name string = group[0][2]
		newplanet := planet{name, orbits, "None"}
		planets = append(planets, newplanet)
	}

	totalsum := 0

	for _, s := range planets {
		totalsum += dfs(planets, s.name)
	}

	fmt.Println(totalsum)

}

func dfs(planets []planet, current string) int {
	count := 0
	found := 0
	for _, s := range planets {
		if s.name == current {
			found = 1
			count++
			current = s.orbits
			break
		}
	}
	if found == 0 {
		return 0
	}
	count += dfs(planets, current)
	return count
}
