import { Component } from '@angular/core';
import { Item } from '../../model/item.model';
import { FoodorderService } from 'src/app/foodorder.service';
import { Router } from '@angular/router';
import { take } from 'rxjs';


@Component({
  selector: 'app-admin-listitem',
  templateUrl: './admin-listitem.component.html',
  styleUrls: ['./admin-listitem.component.css']
})
export class AdminListitemComponent {
  itemList: Array<Item> = [];
  getCategoryList: any[] = [];
  category: any = 100;
  allItemList: Array<Item> = [];
  offset: number = 0;
  pageSize: number = 4; // How many item you want to display in your page.
  totalitem: number = 1;

  constructor(
    private bservice: FoodorderService,
    private router: Router
  ) {
    this.bservice.isUserLoginPresent();
    this.getItemList(true);
  }

  ngOnInit(): void {
    this.getCategoryList = this.bservice.getCategoryList();
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

  delItem(item: Item): void {
    this.bservice.deleteItem(item?.itemId).pipe(take(1)).subscribe(
      (res: any) => {
        alert("Item deleted sucessfully");
        this.getItemList(this.category === 100 || this.category === "100");
      }, err => {
        console.log("Error");
      }
    )
  }

  editItem(item: Item): void {
    this.router.navigate(['/admin/additem'], {
      queryParams: {
        id: item?.itemId
      }
    });

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

}
