import { NavLink } from "react-router-dom";
import styles from '../styles/NavigationBar.module.css';

export default function NavigationBar() {

  return (
    <nav className={styles['nav-bar']}>
      <ul className={styles['nav-bar-list']}>
      <li>
          <NavLink
            to='/' >
            Home
          </NavLink>
        </li>
        <li>
          <NavLink
            to='/set-data' >
            Set Data
          </NavLink>
        </li>
        <li>
          <NavLink
            to='/status' >
            Parking Lot Status
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}