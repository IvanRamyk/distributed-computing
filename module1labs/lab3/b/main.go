package main

import (
	"fmt"
	"sync"
	"time"
)

type Client struct {
	Id     int
	wake   chan struct{}
	finish chan struct{}
}

func (c *Client) walkToBarber(queue chan<- *Client) {
	for {
		queue <- c
		<-c.wake
		fmt.Printf("Client %d woke up and enter to the barbershop\n", c.Id)
		<-c.finish
		fmt.Printf("Client %d got out from barbershop\n", c.Id)
	}
}

func Barber(queue <-chan *Client) {
	for c := range queue {

		c.wake <- struct{}{}

		fmt.Printf("Barber started %d\n", c.Id)
		time.Sleep(1000 * time.Millisecond)
		fmt.Printf("Barber finished %d\n", c.Id)

		c.finish <- struct{}{}
		time.Sleep(200 * time.Millisecond)
	}
}

func main() {
	var wg sync.WaitGroup
	wg.Add(1)

	c1 := Client{finish: make(chan struct{}), Id: 1, wake: make(chan struct{})}
	c2 := Client{finish: make(chan struct{}), Id: 2, wake: make(chan struct{})}
	c3 := Client{finish: make(chan struct{}), Id: 3, wake: make(chan struct{})}
	c4 := Client{finish: make(chan struct{}), Id: 4, wake: make(chan struct{})}

	queue := make(chan *Client)
	go Barber(queue)

	go c1.walkToBarber(queue)
	go c2.walkToBarber(queue)
	go c3.walkToBarber(queue)
	go c4.walkToBarber(queue)

	wg.Wait()
}
