import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import RootLayout from './pages/Root';
import HomePage from './pages/HomePage';
import SetDataPage from './pages/SetDataPage';
import ParkingLotStatusPage from './pages/ParkingLotStatusPage';
import { action as addFloorAction } from './components/ParkingDataForm';

const router = createBrowserRouter([
  {
    path: '/',
    id: 'root',
    element: <RootLayout />,
    children: [
      {
        index: true,
        element: <HomePage />
      },
      {
        path: 'set-data',
        element: <SetDataPage />,
        action: addFloorAction
      },
      {
        path: 'status',
        element: <ParkingLotStatusPage />
      }
    ]
  }
]);

function App() {

  return (
    <RouterProvider router={router} />
  )
}

export default App
