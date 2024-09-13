import { Component, OnInit } from '@angular/core';
import { Item } from '../../model/item.model';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Subject, debounceTime, distinctUntilChanged, take } from 'rxjs';
import { FoodorderService } from 'src/app/foodorder.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})

export class UserHomeComponent implements OnInit {
  itemList: Array<Item> = [];
  quantity: number = 0;
  user: any = {};
  getCategoryList: any[] = [];
  category: any = 100;
  allItemList : Array<Item>= [];
  offset: number = 0;
  pageSize: number = 4; // How many item you want to display in your page.
  totalitem: number = 1;
  searchType: string = "bycategory";
  searchKeyword: string = "";
  userInputUpdate = new Subject<string>();

  constructor(
    private bservice: FoodorderService,
    private router: Router,
    private snakcbar: MatSnackBar
  ) {
   this.bservice.isUserLoginPresent();
    this.getItemList(true);
    this.getUserDetail();

  }


  ngOnInit(): void {
    this.getCategoryList = this.bservice.getCategoryList();
    this.userInputUpdate.pipe(
      debounceTime(400),
      distinctUntilChanged())
      .subscribe(value => {
        if (value.length > 0) {
          this.bservice.searchItemByName(this.searchKeyword, this.offset - 1 < 0 ? 0 : this.offset - 1, this.pageSize).pipe((take(1))).subscribe((res: any) => {
            if (res && res?.item && Array.isArray(res?.item)) {
              this.itemList = res?.item;
              this.allItemList = res?.item;
              this.totalitem = res?.totalItem;
            }
          });
        } else {
          this.itemList = [];
        }
        
      });
  }

  getUserDetail(): void {
    const cid = this.bservice.getUserAuthorization();
    this.bservice.getUserById(cid).pipe(take(1)).subscribe(
      (res: any) => {
        console.log("User***", res);
        if (!!res && res?.userId) {
          this.user = res;
        }
      }, err => {
        console.log("Err");
      }
    )
  }


  getItemList(isAllItem: boolean = false): void {
    let item: any = this.bservice.getAllItems(this.offset - 1 < 0 ? 0 : this.offset - 1, this.pageSize);
    if (!isAllItem) {
      item = this.bservice.getItemByCategory(this.category, this.offset - 1 < 0 ? 0 : this.offset - 1, this.pageSize);
    }
    item.pipe(take(1)).subscribe((res: any) => {
      ;
      if (res && res?.item && Array.isArray(res?.item)) {
        this.itemList = res?.item;
        this.allItemList = res?.item;
        this.totalitem = res?.totalItem;
      }
    }, (err: any) => {
      console.log("Error");
    });
  }

  addToCart(item: Item): void {
    const element: any = document.getElementById(item?.itemId.toString());
  let qty:any= element!==null ? element.value : 0; 
  if(qty ===""){
    element.value=0;
    qty=0;
  }
    if (qty === 0 || qty === "0" || qty <0) {
      alert("Qunatity must be more than zero");
      return ;
    }

    if (qty > item?.quantity) {
      alert('Added quantity should not greater than available quantity');
      return;
    }
    
    const body: any = {
      quantity: qty,
      mrpPrice: item?.mrpPrice,
      item: item,
      user: this.user
    };
    console.log("add to cart", body);
    this.bservice.addToCart(body, item?.itemId, this.user?.userId).pipe(take(1)).subscribe(
      (res: any) => {
        console.log(res);
        if (!!res && res?.cartId) {
        alert("item added sucessfully");
          this.getItemList(true);
          
          
        }
      }, err => {
        console.log("Error");
      }
      
    )
    
  }
  
  getItemByCategory(): void {
    this.offset = 0;
    this.totalitem = 1;
    if (this.category === "100") {
      this.getItemList(true);
    } else {
      this.getItemList(false);
    }
  }

  onNextPageClick(pageOffSet: any): void {
    this.offset = pageOffSet;
    this.getItemList(this.category === 100 || this.category === "100");
  }

  onPreviousPageClick(pageOffSet: any): void {
    this.offset -= 1;
    this.getItemList(this.category === 100 || this.category === "100");
  }

  onFirstPageClick(pageOffSet: any): void {
    this.offset = 0;
    this.getItemList(this.category === 100 || this.category === "100");
  }

  onLastPageClick(pageOffSet: any): void {
    const lastPage = Math.ceil(this.totalitem / this.pageSize);
    this.offset = lastPage;
    this.getItemList(this.category === 100 || this.category === "100");
  }
  gotocartList(): void {
    this.router.navigate(["/user/cart"]);
  }
  
  getSelectedType(event: any): void {
    this.searchType = event?.value;
    if (this.searchType === "bysearch") {
      this.itemList = [];
    } else {
      //All category dropdown
      this.getItemList(true);
    }
  }

  getSearchWord(ev: any): void {
    setTimeout(() => {
      this.userInputUpdate.next(this.searchKeyword);
    }, 100);
  }

}