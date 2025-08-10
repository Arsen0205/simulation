package org.example.simulation;

import org.example.world.World;
import org.example.world.WorldInitializer;
import org.example.world.WorldRender;

import java.util.Scanner;

public class Simulation {
    private final static int ONE_MOVE = 1;
    private final static int THE_ENDLESS_LOOP = 2;
    private final static int QUIT = 3;

    private final static String PAUSE = "P";

    private final World world;
    private final WorldRender renderer;
    private final WorldInitializer worldInitializer;
    private final Actions actions;
    private final Scanner scanner = new Scanner(System.in);

    private boolean isRunning = false;
    private int turnCount = 0;

    public Simulation(int width, int height) {
        this.world = new World(width, height);
        this.worldInitializer = new WorldInitializer(world);
        this.worldInitializer.generateWorld(10, 20, 3, 5);
        this.actions = new Actions(world);
        this.renderer = new WorldRender(world);
    }

    public void nextTurn() {
        turnCount++;
        actions.turnActions();
        renderer.render();
        System.out.printf("Ход #%d завершен\n", turnCount);
    }

    public void startSimulation() {
        isRunning = true;
        System.out.printf("Симуляция запущена. Нажмите %s для паузы \n", PAUSE);

        Thread simulationThread = new Thread(() -> {
            while (isRunning) {
                nextTurn();
                System.out.printf("Нажмите %s для паузы \n", PAUSE);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        simulationThread.start();

        while (isRunning) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("P")) {
                pauseSimulation();
            }
        }
    }

    public void pauseSimulation() {
        isRunning = false;
        System.out.println("Симуляция приостановлена");
    }

    public void runInteractive() {
        actions.initActions();
        while (true) {
            System.out.printf("%d - Следующий ход | %d - Автосимуляция | %d - Выход  \n", ONE_MOVE, THE_ENDLESS_LOOP, QUIT);
            String input = scanner.nextLine();

            if (input.equals("1")) {
                nextTurn();
            } else if (input.equals("2")) {
                startSimulation();
            } else if (input.equalsIgnoreCase("3")) {
                break;
            }
        }
    }
}