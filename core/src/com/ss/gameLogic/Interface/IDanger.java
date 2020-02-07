package com.ss.gameLogic.Interface;

import com.ss.gameLogic.objects.Boat;

public interface IDanger {
  void fire(Boat boat);
  void chkWin();
  void endGame();
}
