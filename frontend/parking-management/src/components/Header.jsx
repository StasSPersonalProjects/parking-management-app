import styles from '../styles/Header.module.css';

export default function Header({ children }) {

  return (
    <header className={styles['header-position']}>
      <h2 className={styles['header-text']}>
        {children}
      </h2>
    </header>
  );
}