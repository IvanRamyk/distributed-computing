package main

import "sync"

type Graph struct {
	lock   sync.RWMutex
	prices [][]int
	size   int
}

func (g *Graph) AddNode() {
	g.lock.Lock()
	g.size++
	g.prices = append(g.prices, make([]int, g.size))
	for i := 0; i < g.size; i++ {
		g.prices[i] = append(g.prices[i], -1)
	}
	g.lock.Unlock()
}

func (g *Graph) DeleteVertex(vertex int) {
	g.lock.Lock()
	if vertex < g.size {
		if g.size > 0 {
			g.size--
			g.prices[vertex] = g.prices[g.size]
			g.prices = g.prices[:g.size]
			for i := 0; i < g.size; i++ {
				g.prices[i][vertex] = g.prices[i][g.size]
				g.prices[i] = g.prices[i][:g.size]
			}
		}
	}
	g.lock.Unlock()
}

func (g *Graph) SetEdge(start, end, weight int) {
	g.lock.Lock()
	if start < g.size && end < g.size {
		g.prices[start][end] = weight
		g.prices[end][start] = weight
	}
	g.lock.Unlock()
}

func (g *Graph) FindPathWeight(from, to int) int {
	g.lock.RLock()
	weight := -1
	if from < g.size && to < g.size {
		dist := make([]int, g.size)
		for i := 0; i < g.size; i++ {
			dist[i] = -1
		}
		dist[from] = 0
		q := Queue{[]int{from}}
		for !q.Empty() {
			cur := q.Front()
			q.Pop()
			for i := 0; i < g.size; i++ {
				if g.prices[cur][i] != -1 && dist[i] == -1 {
					dist[i] = dist[cur] + g.prices[cur][i]
					q.Push(i)
				}
			}
		}
		weight = dist[to]
	}
	g.lock.RUnlock()
	return weight
}

func (g *Graph) VerticesNumber() int {
	g.lock.RLock()
	value := g.size
	g.lock.RUnlock()
	return value
}

func (g *Graph) TicketPrice(from, to int) int {
	g.lock.RLock()
	value := -1
	if from < g.size && to < g.size {
		value = g.prices[from][to]
	}
	g.lock.RUnlock()
	return value
}
