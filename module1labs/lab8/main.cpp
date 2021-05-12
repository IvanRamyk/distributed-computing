#include <mpi.h>
#include <cstdio>
#include <ctime>
#include <random>
#include <chrono>

int ind(int i, int j, int N) {
    return i * N + j;
}

void str_main_process(int * A, int *B, int *C, int matrix_size){
    int processes_number;
    int process_id ;
    MPI_Status status;

    MPI_Comm_rank(MPI_COMM_WORLD, &process_id);
    MPI_Comm_size(MPI_COMM_WORLD, &processes_number);


    int rows_in_task = matrix_size / processes_number;
    int ITEM_OFFSET = rows_in_task;

    std::cout << rows_in_task << std::endl;

    for (int pid = 1; pid < processes_number; pid++) {
        MPI_Send(&matrix_size, 1, MPI_INT, pid, 0, MPI_COMM_WORLD);
        MPI_Send(&ITEM_OFFSET, 1, MPI_INT, pid, 0, MPI_COMM_WORLD);
        MPI_Send(&rows_in_task, 1, MPI_INT, pid, 0, MPI_COMM_WORLD);
        MPI_Send(&A[ITEM_OFFSET * matrix_size], rows_in_task * matrix_size, MPI_INT, pid, 0, MPI_COMM_WORLD);
        MPI_Send(B, matrix_size * matrix_size, MPI_INT, pid, 0, MPI_COMM_WORLD);

        ITEM_OFFSET += rows_in_task;
    }

    for (int i = 0; i < matrix_size; i++) {
        for (int j = 0; j < rows_in_task; j++) {
            C[ind(j, i, matrix_size)] = 0;
            for (int k = 0; k < matrix_size; k++) {
                C[ind(j, i, matrix_size)] += A[ind(j, k, matrix_size)] * B[ind(k, i, matrix_size)];
            }
        }
    }

    for (int i = 1; i < processes_number; i++) {
        MPI_Recv(&ITEM_OFFSET, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(&rows_in_task, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(&C[ITEM_OFFSET * matrix_size], rows_in_task * matrix_size, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
    }
}

void str_processor() {
    int matrix_size;
    int number_rows_to_handle;
    int start_row;
    MPI_Status status;
    MPI_Recv(&matrix_size, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

    int *A = new int[matrix_size * matrix_size];
    int *B = new int[matrix_size * matrix_size];
    int *C = new int[matrix_size * matrix_size];

    MPI_Recv(&start_row, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
    MPI_Recv(&number_rows_to_handle, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
    MPI_Recv(&A[start_row * matrix_size], number_rows_to_handle * matrix_size, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
    MPI_Recv(B, matrix_size * matrix_size, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

    for (int i = 0; i < matrix_size; i++) {
        for (int j = start_row; j < number_rows_to_handle + start_row; j++) {
            C[ind(j, i, matrix_size)] = 0;
            for (int k = 0; k < matrix_size; k++) {
                C[ind(j, i, matrix_size)] += A[ind(j, k, matrix_size)] * B[ind(k, i, matrix_size)];
            }
        }
    }

    MPI_Send(&start_row, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    MPI_Send(&number_rows_to_handle, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    MPI_Send(&C[start_row * matrix_size], number_rows_to_handle * matrix_size, MPI_INT, 0, 0, MPI_COMM_WORLD);
}

bool DEMO = false;


int main(int argc, char** argv) {



    MPI_Init(nullptr, nullptr);
    int processor_id;
    int processors_number;
    MPI_Comm_rank(MPI_COMM_WORLD, &processor_id);
    MPI_Comm_size(MPI_COMM_WORLD, &processors_number);
    if (processor_id == 0) {
        std::mt19937 engine;
        engine.seed(std::time(nullptr));

        int matrix_size;
        std::cin >> matrix_size;

        int *A = new int[matrix_size * matrix_size];
        int *B = new int[matrix_size * matrix_size];
        int *C = new int[matrix_size * matrix_size];
        for (int i = 0; i < matrix_size * matrix_size; ++i) {
            A[i] = -100 + (engine() % 201);
            B[i] = -100 + (engine() % 201);
        }

        auto start = std::chrono::high_resolution_clock::now();
        str_main_process(A, B, C, matrix_size);
        auto stop = std::chrono::high_resolution_clock::now();
        auto duration = std::chrono::duration_cast<std::chrono::milliseconds>(stop - start);
        std::cout << "Time: " << duration.count() << " milliseconds" << std::endl;
        if (DEMO) {
            for (int i = 0; i < matrix_size; ++i) {
                for (int j = 0; j < matrix_size; ++j) {
                    std::cout << C[ind(i,j, matrix_size)] << " ";
                }
                std::cout << std::endl;
            }
        }
    } else {
        str_processor();
    }


    MPI_Finalize();
}
