package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockolt1;
  private TorpedoStore mockolt2;

  @BeforeEach
  public void init(){
    mockolt1 = mock(TorpedoStore.class);
    mockolt2 = mock(TorpedoStore.class);
    this.ship = new GT4500(mockolt1, mockolt2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(true);
    when(mockolt2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(true);
    when(mockolt2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    verify(mockolt2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(false);
    when(mockolt2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    verify(mockolt2, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_Single_First_Empty_Success(){
    // Arrange
    when(mockolt1.isEmpty()).thenReturn(true);
    when(mockolt2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(1)).isEmpty();
    verify(mockolt1, times(0)).fire(1);
    verify(mockolt2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Both_Empty_Failure(){
    // Arrange
    when(mockolt1.isEmpty()).thenReturn(true);
    when(mockolt2.isEmpty()).thenReturn(true);
    

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(1)).isEmpty();
    verify(mockolt2, times(1)).isEmpty();
    verify(mockolt1, times(0)).fire(1);
    verify(mockolt2, times(0)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Failure_Store1(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(false);
    when(mockolt2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    verify(mockolt2, times(1)).fire(1);
    assertEquals(false, result);
  }
  @Test
  public void fireTorpedo_All_Failure_Store2(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(true);
    when(mockolt2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    verify(mockolt2, times(1)).fire(1);
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_primaryFired_thenSecondary_fired(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(false);
    when(mockolt2.fire(1)).thenReturn(true);
    when(mockolt2.isEmpty()).thenReturn(false);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(1)).fire(1);
    verify(mockolt2, times(1)).fire(1);
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_primaryFired_thenSecondary_empty(){
    // Arrange
    when(mockolt1.fire(1)).thenReturn(true);
    when(mockolt2.fire(1)).thenReturn(true);
    when(mockolt2.isEmpty()).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockolt1, times(2)).fire(1);
    verify(mockolt2, times(0)).fire(1);
    assertEquals(true, result);
  }

}
