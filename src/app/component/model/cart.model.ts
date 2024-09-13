import { Item } from "./item.model";

export interface Cart{
    cartId : number;
    mrpPrice : number;
    quantity : number;
    user : any;
    item: Item
}