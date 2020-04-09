package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Console;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primaryStore;
  private TorpedoStore secondaryStore;

  @BeforeEach
  public void init(){
    primaryStore = mock(TorpedoStore.class);
    secondaryStore = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryStore, secondaryStore);
  }

  @Test
  public void fireTorpedo_Single_Success() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).isEmpty();
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Empty() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Secondary_Empty() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result &= ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(2)).isEmpty();
    verify(primaryStore, times(2)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(0)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Primary_Failure() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(0)).isEmpty();
    verify(secondaryStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Full_Success() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Half_Success() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(false);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primaryStore, times(1)).isEmpty();
    verify(primaryStore, times(1)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Alternating() {
    // Arrange
    when(primaryStore.isEmpty()).thenReturn(false);
    when(primaryStore.fire(1)).thenReturn(true);
    when(secondaryStore.isEmpty()).thenReturn(false);
    when(secondaryStore.fire(1)).thenReturn(true);

    // Act
    boolean primaryResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondaryResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean thirdResult = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, primaryResult && secondaryResult && thirdResult);
    verify(primaryStore, times(2)).isEmpty();
    verify(primaryStore, times(2)).fire(1);
    verify(secondaryStore, times(1)).isEmpty();
    verify(secondaryStore, times(1)).fire(1);
  }
}
