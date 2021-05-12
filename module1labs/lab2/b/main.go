package main

import (
	"fmt"
	"time"
)

type Product struct {
	name string
	cost int
}

func IvanovAlgo(stock []Product, fromStock chan Product)  {
	for _, product:= range stock {
		fromStock <- product
		time.Sleep(200 * time.Millisecond)
		fmt.Printf("Ivanov took %s outside.\n", product.name)
	}
	fromStock <- Product{"", -1}
}

func PetrovAlgo(fromStock chan Product, toTransport chan Product) {
	for {
		product:= <- fromStock
		time.Sleep(500 * time.Millisecond)
		fmt.Printf("Petrov loaded %s\n", product.name)
		toTransport <- product
		if product.cost == -1 {
			return
		}
	}
}

func NechyporchukAlgo(toTransport chan Product) int {
	sum := 0
	for {
		good := <- toTransport
		time.Sleep(100 * time.Millisecond)
		if good.cost == -1 {
			return sum
		}
		sum += good.cost
	}
}


func main() {
	stock:=[]Product{
		{"apple", 10},
		{"banana", 20},
		{"p1", 5},
		{"p2", 40},
		{"p3", 100},
		{"p4", 70},
		{"p5", 40},
		{"p6", 1},
	}
	fromStock:= make(chan Product, 100)
	toTransport := make(chan Product, 100)
	go IvanovAlgo(stock, fromStock)
	go PetrovAlgo(fromStock, toTransport)
	// := make(chan int, 100)

	fmt.Printf("Total: %d", NechyporchukAlgo(toTransport))
}
