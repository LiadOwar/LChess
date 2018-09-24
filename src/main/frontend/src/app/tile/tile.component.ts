import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-tile',
  templateUrl: './tile.component.html',
  styleUrls: ['./tile.component.css']
})
export class TileComponent {
  readonly ROOT_URL = 'http://localhost:8080';

  turn: any;
  originPos: any;
  targetPos: any;
  lines = [];
  tiles = [] ;

  constructor(private http: HttpClient) {
    // this.tiles = ['white', 'black'];
    let i: number;
    let startLineColor = 'white';
    this.tiles[0] = startLineColor;
    for (i = 1; i < 64 ; i++) {
      let prevTileColor = this.tiles[i - 1];
      let currentTileColor = this.tiles[i];
      if (i % 8 === 0 && i !== 0) {
        currentTileColor = prevTileColor;
        this.tiles[i] = currentTileColor;
        i++;
      }
      if (prevTileColor === 'black') {
        currentTileColor = 'white';
      } else
      if (prevTileColor === 'white') {
        currentTileColor = 'black';
      }
      this.tiles[i] = currentTileColor;
    }
  }
  getTurn() {
    this.turn = this.http.get(this.ROOT_URL + '/game/turn', {observe: 'response'})
      .subscribe(resp => {
        this.turn = resp.body;
        console.log(this.turn);
        return this.turn;
      });
  }

  startGame() {
    this.http.get(this.ROOT_URL + '/game/start', {observe: 'response'})
      .subscribe(resp => {this.getAll();
        this.getTurn();
      });
  }
  movement(post) {
    console.log(post.position.position.xPos + ',' + post.position.position.yPos);
    if (this.originPos == null) {
      this.originPos = post.position.position.xPos + ',' + post.position.position.yPos;
    } else {
      this.targetPos = post.position.position.xPos + ',' + post.position.position.yPos;
      this.http.post(this.ROOT_URL + '/game/move', {
        startPosition : this.originPos,
        destPosition : this.targetPos
      }).subscribe( res => {

        this.getAll();
        this.getTurn();
      });
      this.originPos = null;
      this.targetPos = null;

    }
  }
  getAll() {
    this.http.get(this.ROOT_URL + '/game', {observe: 'response'}).subscribe(resp => {
      for (let i = 0 ; i < 64 ; i++) {
        this.lines[i] = resp.body[i];
      }
    });
  }


  getLines() {
    this.getAll();
  }

}
