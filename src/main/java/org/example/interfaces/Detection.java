package org.example.interfaces;

import org.example.world.Coordinates;
import org.example.world.World;

public interface Detection {
    Coordinates findNearestTarget(World world);
}
