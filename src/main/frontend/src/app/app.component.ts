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

  turn : any;
  originPos : any;
  targetPos : any;
  posts: any;
  line1: any;
  line2: any;
  line3: any;
  line4: any;
  line5: any;
  line6: any;
  line7: any;
  line8: any;
  lines: any;

  resp: any;

  constructor (private http: HttpClient) {}

  getTurn(){
    this.turn = this.http.get(this.ROOT_URL + '/game/turn', {observe: 'response'})
    .subscribe(resp => {
                          this.turn = resp.body;
                          console.log(this.turn);
                          return this.turn;
                         });
  }

  startGame(){
      this.http.get(this.ROOT_URL + '/game/start',{observe: 'response'})
      .subscribe(resp => {this.getAll();
                                this.getTurn();

                               });
    }
  movement(post) {
    console.log(post.position.position.xPos + "," + post.position.position.yPos);
    if (this.originPos == null) {
     this.originPos = post.position.position.xPos + "," + post.position.position.yPos;
    } else {
      this.targetPos = post.position.position.xPos + "," + post.position.position.yPos;
      this.http.post(this.ROOT_URL + '/game/move',{
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

     }
    }


  getLine1() {
   var line1Array = [];
   this.line1 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
   for (var i = 0 ; i < 8 ; i++){
    line1Array[i] = (res[i]);
   }
   this.line1 = line1Array;
   return this.line1;
   }) ;
  }

    getLine2() {
     var line2Array = [];
     this.line2 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
     for (var i = 0 ; i < 8 ; i++){
      line2Array[i] = (res[i + 8]);
     }
     this.line2 = line2Array;
     return this.line2;
     }) ;
    }

    getLine3() {
     var line3Array = [];
     this.line3 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
     for (var i = 0 ; i < 8 ; i++){
      line3Array[i] = (res[i + 16]);
     }
     this.line3 = line3Array;
     return this.line3;
     }) ;
    }

    getLine4() {
     var line4Array = [];
     this.line4 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
     for (var i = 0 ; i < 8 ; i++){
      line4Array[i] = (res[i + 24]);
     }
     this.line4 = line4Array;
     return this.line4;
     }) ;
    }

   getLine5() {
       var line5Array = [];
       this.line5 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
       for (var i = 0 ; i < 8 ; i++){
        line5Array[i] = (res[i + 32]);
       }
       this.line5 = line5Array;
       return this.line5;
       }) ;
      }

   getLine6() {
         var line6Array = [];
         this.line6 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
         for (var i = 0 ; i < 8 ; i++){
          line6Array[i] = (res[i + 40]);
         }
         this.line6 = line6Array;
         return this.line6;
         }) ;
        }

 getLine7() {
         var line7Array = [];
         this.line7 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
         for (var i = 0 ; i < 8 ; i++){
          line7Array[i] = (res[i + 48]);
         }
         this.line7 = line7Array;
         return this.line7;
         }) ;
        }


 getLine8() {
         var line8Array = [];
         this.line8 = this.http.get(this.ROOT_URL + '/game').map(function(res) {
         for (var i = 0 ; i < 8 ; i++){
          line8Array[i] = (res[i + 56]);
         }
         this.line8 = line8Array;
         return this.line8;
         }) ;
        }

  getPosts() {
    this.getLine1();
    this.getLine2();
    this.getLine3();
    this.getLine4();
    this.getLine5();
    this.getLine6();
    this.getLine7();
    this.getLine8();
  }
   getLines() {
      this.getAll();
    }


}
