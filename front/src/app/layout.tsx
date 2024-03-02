"use client"
import type { Metadata } from "next";
import { Open_Sans } from "next/font/google";
import "./globals.css";
import "@/bibliotecas/animate.min.css";
import "@/bibliotecas/boxicons/css/boxicons.min.css";
import "@/bibliotecas/remixicon/remixicon.css";
import "@/bibliotecas/swiper/swiper-bundle.min.css";
import "@/bibliotecas/swiper/swiper-bundle.min.js";

import 'aos/dist/aos.css';

import Script from "next/script";
import Header from "@/componentes/core/header/Header";
import Footer from "@/componentes/core/footer/Footer";
import AOS from 'aos';
import { useEffect } from "react";

const OpenSans = Open_Sans({ subsets: ["latin"] });


export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {  

  useEffect(()=>{
      AOS.init();
  },[])

  return (    
    <html lang="pt-BR">
       <head>
         <title>Minerva</title> 
         <meta name="description" content="Plataforma de cursos on-line" />
         <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"  crossOrigin="anonymous"/>
         <link rel="stylesheet" href="https://unpkg.com/aos@next/dist/aos.css" />
       
         <Script 
          src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" 
          integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"          
          crossOrigin="anonymous"/>
           
       </head>
      <body className={OpenSans.className}>
        <Header/>
        {children}
        <Footer/> 
       </body>  
            
    </html>
  );
}
