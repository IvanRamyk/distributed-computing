package main

import (
	"fmt"
	"math/rand"
	"time"
)

type Recruit struct {
	isLeft bool
}

func (recruit *Recruit) IsLeft() bool {
	return recruit.isLeft
}

func (recruit *Recruit) IsRight() bool {
	return !recruit.isLeft
}

func armyThread(recs *[][]Recruit, left, right int, start chan int, res chan bool) {
	for {
		st := <-start
		if st == -1 {
			return
		}
		isStopped := true
		for i := left; i < right; i++ {
			if i < len((*recs)[0])-1 && (*recs)[0][i].IsRight() && (*recs)[0][i+1].IsLeft() {
				(*recs)[1][i] = Recruit{true}
				(*recs)[1][i+1] = Recruit{false}
				isStopped = false
			}
		}
		res <- isStopped
	}
}

func minInt(a, b int) int {
	if a < b {
		return a
	}
	return b
}

func armyManager(recs *[][]Recruit) {
	for i := 0; i < len((*recs)[0]); i++ {
		sl := '<'
		if (*recs)[0][i].IsRight() {
			sl = '>'
		}
		fmt.Printf("%c", sl)
	}
	fmt.Printf("\n")
	goNumber := 2
	getResult := make(chan bool)
	startChans := make([]chan int, goNumber)
	for i := 0; i < goNumber; i++ {
		startChans[i] = make(chan int)
		go armyThread(recs, 50*i, minInt(50*(i+1), len((*recs)[0])), startChans[i], getResult)
	}
	for {
		*recs = append(*recs, make([]Recruit, 100))
		for i := 0; i < len((*recs)[0]); i++ {
			(*recs)[1][i] = (*recs)[0][i]
		}
		for i := 0; i < goNumber; i++ {
			startChans[i] <- 1
		}
		isStopped := true
		for i := 0; i < goNumber; i++ {
			res := <-getResult
			isStopped = isStopped && res
		}
		*recs = (*recs)[1:]
		for i := 0; i < len((*recs)[0]); i++ {
			sl := '<'
			if (*recs)[0][i].IsRight() {
				sl = '>'
			}
			fmt.Printf("%c", sl)
		}
		fmt.Printf("\n")
		time.Sleep(time.Millisecond * 50)

		if isStopped {
			for i := 0; i < goNumber; i++ {
				startChans[i] <- -1
			}
			break
		}
	}
}

func main() {
	recs := make([][]Recruit, 0)
	recs = append(recs, make([]Recruit, 100))
	s1 := rand.NewSource(time.Now().UnixNano())
	r1 := rand.New(s1)
	for i := 0; i < 100; i++ {
		recs[0][i] = Recruit{r1.Intn(2) == 0}
	}
	armyManager(&recs)
}
