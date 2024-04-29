import { Outlet } from "react-router-dom";
import NavigationBar from "../components/NavigationBar";

export default function RootLayout() {
  return (
    <>
      <NavigationBar />
      <main>
        <Outlet />
      </main>
    </>
  );
}