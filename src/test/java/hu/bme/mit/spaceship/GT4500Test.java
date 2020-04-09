package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore torpedoStore;

  @BeforeEach
  public void init(){
    torpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(torpedoStore, torpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(torpedoStore.isEmpty()).thenReturn(false);
    when(torpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore, times(1)).isEmpty();
    verify(torpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(torpedoStore.isEmpty()).thenReturn(false);
    when(torpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(torpedoStore, times(2)).isEmpty();
    verify(torpedoStore, times(2)).fire(1);
  }
}
