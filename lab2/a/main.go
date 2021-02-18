package main

import (
	"fmt"
	"time"
)

func min(a int, b int) int {
	if a < b {
		return a
	}
	return b
}

func seekAndDestroy(
	beeId int,
	square[][] int,
	xLeft, xRight, yLeft, yRight int,
	ch chan struct {x int; y int},
	beeQueue chan int) {
	for row := xLeft; row < xRight; row++ {
		for column := yLeft; column < yRight; column++ {
			if square[row][column] == 1 {
				ch <- struct {x int; y int} {row, column}
				fmt.Printf("Bee %d: (%d, %d) Winnie-the-Pooh is found!\n", beeId, row, column)
				return
			}
			fmt.Printf("Bee %d: (%d, %d) is empty\n", beeId, row, column)
			time.Sleep(1000 * time.Millisecond)
		}
	}
	beeQueue <- beeId
}

func manager(field[][] int, squareSize int, beesNumber int) struct {x int; y int} {
	rows, columns := len(field), len(field[0])
	ch := make(chan struct {x int; y int})
	beeQueue := make(chan int, beesNumber)
	xLeft, yLeft := 0, 0
	for i := 0; i < beesNumber; i++ {
		beeQueue <- i
	}
	allTasksStarted := false
	for {
		select {
		case id := <- beeQueue:
			if !allTasksStarted {
				xRight := min(xLeft+squareSize, rows)
				yRight := min(yLeft+squareSize, columns)
				fmt.Printf("New task for Bee %d (%d %d) -> (%d %d)\n", id, xLeft, yLeft, xRight, yRight)
				go seekAndDestroy(id, field, xLeft, xRight, yLeft, yRight, ch, beeQueue)
				xLeft += squareSize
				if xLeft >= rows {
					xLeft = 0
					yLeft += squareSize
				}
				if yLeft >= columns {
					allTasksStarted = true
				}
			}
		case searchResult := <- ch:
			return searchResult
		}
	}
}

func main() {

	var field [][]int
	for i := 0; i < 10; i++ {
		tmp := make([]int, 10)
		field = append(field, tmp)
	}
	var x, y int
	_, _ =fmt.Scan(&x, &y)
	field[x][y] = 1
	result := manager(field, 3, 4)
	fmt.Printf("%d %d\n", result.x, result.y)
}
