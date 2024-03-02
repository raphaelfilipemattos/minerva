import styles from "./page.module.css";
import HomeComponent from "@/componentes/core/home/HomeComponent";

export default function Home() {

  return (   
    <>
      <main className={styles.main}>      
          <HomeComponent/>
      </main>
    </> 
  );
}
