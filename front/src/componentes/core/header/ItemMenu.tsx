import Link from "next/link";
import { EventHandler, ReactNode } from "react";

export default function ItemMenu({
    children,
    url,
    className
  }: {
    children: React.ReactNode
    url: string,
    className?: string
    
  }){
    const classe = ! className ? "" : className;
    return(
        <li className= " nav-item ">
          <Link          
             className={classe +""}
             aria-current="page"
             href={url}>{children}</Link>             
        </li>
    );
}