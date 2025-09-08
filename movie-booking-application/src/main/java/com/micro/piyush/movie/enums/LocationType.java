package com.micro.piyush.movie.enums;

import java.util.Arrays;

public enum LocationType {
    BANGALORE("Bangalore"),
    CHENNAI("Chennai"),
    DELHI("Delhi"),
    MUMBAI("Mumbai"),
    HYDERABAD("Hyderabad");

    private final String displayName;

    /**
     * Constructor for the enum.
     * @param displayName The user-friendly string representation of the location.
     */
    LocationType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the user-friendly string representation of the enum constant.
     * @return The display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a string representation of a location to its corresponding LocationType enum constant.
     * The comparison is case-insensitive.
     *
     * @param locationString The string to convert.
     * @return The LocationType enum constant.
     * @throws IllegalArgumentException if the provided string does not match any enum constant.
     */
    public static LocationType fromString(String locationString) {
        // Iterate through all enum constants to find a match
        return Arrays.stream(LocationType.values())
                .filter(type -> type.getDisplayName().equalsIgnoreCase(locationString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown location: " + locationString));
    }
}
