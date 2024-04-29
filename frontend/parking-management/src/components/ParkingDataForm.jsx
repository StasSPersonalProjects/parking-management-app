import { Form, redirect } from 'react-router-dom';
import styles from '../styles/ParkingDataForm.module.css';
import { addParkingPlacesURL } from '../util/urls';

export default function ParkingDataForm() {

  return (
    <div className={styles['form-container']}>
      <Form method='post' className={styles['form-field']}>
        <div className={styles['input-field']}>
          <input id='floor' type='text' name='floor' placeholder='Floor' required />
        </div>
        <div  className={styles['input-field']}>
          <input id='parkingPlaces' type='text' name='parkingPlaces' placeholder='Parking Places' required />
        </div>
        <button className={styles['form-button']}>Submit</button>
      </Form>
    </div>
  );
}

export async function action({ request }) {
  const data = await request.formData();
  const floorData = {
    floor: data.get('floor'),
    parkingPlaces: data.get('parkingPlaces')
  }

  try {
    await fetch(addParkingPlacesURL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(floorData)
    });
  } catch (error) {
      console.error('An error occurred: ' + error);
  }

  return redirect('/status');

}