import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  readonly ROOT_URL = 'http://localhost:8080';

  turn: any;
  originPos: any;
  targetPos: any;
  lines: any;

  constructor (private http: HttpClient) {}

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
     var lineArray = [];
     this.lines = this.http.get(this.ROOT_URL + '/game').map(function(res) {
     for (var i = 0 ; i < 64 ; i++){
         lineArray[i] = (res[i]);
        }
        this.lines = lineArray;
        return this.lines;
     });
    }


    getLines() {
      this.getAll();
    }


}

