package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func addRoad(graph *Graph) {
	var s1 = rand.NewSource(time.Now().UnixNano())
	var r1 = rand.New(s1)
	for {
		if graph.VerticesNumber() > 1 {
			from := r1.Intn(graph.VerticesNumber())
			to := r1.Intn(graph.VerticesNumber())
			for from == to {
				from = r1.Intn(graph.VerticesNumber())
				to = r1.Intn(graph.VerticesNumber())
			}
			if graph.TicketPrice(from, to) != -1 {
				price := -1
				graph.SetEdge(from, to, price)
				fmt.Printf("Edge was deleted: city %d - city %d\n", from, to)
			} else {
				price := r1.Intn(100)
				graph.SetEdge(from, to, price)
				fmt.Printf("New edge: city %d - city %d: %d!\n", from, to, price)
			}
		}
		time.Sleep(time.Millisecond * 1000)
	}
}

func changeTicketPrice(graph *Graph) {
	var s1 = rand.NewSource(time.Now().UnixNano())
	var r1 = rand.New(s1)
	for {
		if graph.VerticesNumber() > 1 {
			from := r1.Intn(graph.VerticesNumber())
			to := r1.Intn(graph.VerticesNumber())
			for from == to {
				from = r1.Intn(graph.VerticesNumber())
				to = r1.Intn(graph.VerticesNumber())
			}
			if graph.TicketPrice(from, to) != -1 {
				price := r1.Intn(100)
				graph.SetEdge(from, to, price)
				fmt.Printf("New price: city %d - city %d: %d\n", from, to, price)
			}
		}
		time.Sleep(time.Millisecond * 1000)
	}
}

func citiesManager(graph *Graph) {
	var s1 = rand.NewSource(time.Now().UnixNano())
	var r1 = rand.New(s1)
	for {
		value := r1.Intn(4)
		if value == 3 && graph.VerticesNumber() > 1 {
			vertex := r1.Intn(graph.VerticesNumber())
			graph.DeleteVertex(vertex)
			fmt.Printf("Vertex %d was deleted.\n", vertex)
		} else {
			graph.AddNode()
			fmt.Printf("Vertex %d was added!\n", graph.VerticesNumber()-1)
		}
		time.Sleep(2000 * time.Millisecond)
	}
}

func evaluatePathWeight(graph *Graph) {
	var s1 = rand.NewSource(time.Now().UnixNano())
	var r1 = rand.New(s1)
	for {
		if graph.VerticesNumber() > 1 {
			from := r1.Intn(graph.VerticesNumber())
			to := r1.Intn(graph.VerticesNumber())
			for from == to {
				from = r1.Intn(graph.VerticesNumber())
				to = r1.Intn(graph.VerticesNumber())
			}
			fmt.Printf("\t\tFound path from %d to %d: %d!!!\n", from, to, graph.FindPathWeight(from, to))
		}
		time.Sleep(time.Millisecond * 3000)
	}
}

func main() {
	graph := &Graph{sync.RWMutex{}, make([][]int, 0), 0}
	go citiesManager(graph)
	go changeTicketPrice(graph)
	go addRoad(graph)
	go evaluatePathWeight(graph)
	select {}
}
