package com.bookspot;

import com.bookspot.global.Events;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class EventMockingHelper {
    public static void runWithoutEvents(Runnable block) {
        try (MockedStatic<Events> mocked = Mockito.mockStatic(Events.class)) {
            mocked.when(() -> Events.raise(Mockito.any())).thenAnswer(invocation -> null);
            block.run();
        }
    }
}
