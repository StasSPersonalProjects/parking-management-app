import styles from '../styles/Header.module.css';

export default function Header({ title }) {

  return (
    <header className={styles['header-position']}>
      <h2 className={styles['header-text']}>
        {title}
      </h2>
    </header>
  );
}