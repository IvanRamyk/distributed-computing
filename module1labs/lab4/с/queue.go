package main

type Queue struct {
	container []int
}

func (queue *Queue) Push(value int) {
	queue.container = append(queue.container, value)
}

func (queue *Queue) Pop() {
	if !queue.Empty() {
		queue.container = queue.container[1:]
	}
}

func (queue *Queue) Front() int {
	return queue.container[0]
}

func (queue *Queue) Empty() bool {
	return len(queue.container) == 0
}
