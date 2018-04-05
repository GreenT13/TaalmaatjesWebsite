import {Component, ContentChild, Input, OnChanges, Output, SimpleChanges, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-table-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnChanges {
  // Template which fill the table.
  @ContentChild('templateItem') templateItem;
  @ContentChild('templateHeader') templateHeader;

  // Event emitter when an item is double clicked.
  @Output()
  onDblClick: EventEmitter<any> = new EventEmitter<any>();

  // The list that is shown.
  @Input()
  completeList: any[];

  // Determine how many buttons are shown in the bottom.
  maxNrOfPageButtons: number = 4;

  // Number of items to show on a page.
  pageSize : number = 10;

  // Variables for the algorithm.
  nrOfPageButtons : number;
  pageNumber : number = 0;
  currentIndex : number = 1;
  itemsOnPage: any[];
  pagesIndex : Array<number>;
  pageStart : number = 1; // Kind off a constant, but no reason to change this to something different than 1.

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.completeList.currentValue) {
      this.completeList = changes.completeList.currentValue;
      this.init();
    }
  }

  init(){
    this.currentIndex = 1;
    this.pageStart = 1;
    this.nrOfPageButtons = this.maxNrOfPageButtons;

    this.pageNumber = parseInt(""+ (this.completeList.length / this.pageSize));
    if(this.completeList.length % this.pageSize != 0){
      this.pageNumber ++;
    }

    if(this.pageNumber  < this.nrOfPageButtons){
      this.nrOfPageButtons =  this.pageNumber;
    }

    this.refreshItems();
  }

  fillArray(): any{
    let obj = [];
    for(let index = this.pageStart; index < this.pageStart + this.nrOfPageButtons; index ++) {
      obj.push(index);
    }
    return obj;
  }
  refreshItems(){
    this.itemsOnPage = this.completeList.slice((this.currentIndex - 1)*this.pageSize, (this.currentIndex) * this.pageSize);
    this.pagesIndex =  this.fillArray();
  }
  prevPage(){
    if(this.currentIndex>1){
      this.currentIndex --;
    }
    if(this.currentIndex < this.pageStart){
      this.pageStart = this.currentIndex;
    }
    this.refreshItems();
  }
  nextPage(){
    if(this.currentIndex < this.pageNumber){
      this.currentIndex ++;
    }
    if(this.currentIndex >= (this.pageStart + this.nrOfPageButtons)){
      this.pageStart = this.currentIndex - this.nrOfPageButtons + 1;
    }

    this.refreshItems();
  }
  setPage(index : number){
    this.currentIndex = index;
    this.refreshItems();
  }
}
