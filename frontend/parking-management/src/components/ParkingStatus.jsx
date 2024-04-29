import { useState, useEffect, useRef } from "react";
import SockJS from "sockjs-client";
import Stomp from 'stompjs';

import StatusPart from "./StatusPart";
import styles from '../styles/ParkingStatus.module.css';
import { webSocketURL, webSocketURLtopic, parkingDataURL } from "../util/urls";

export default function ParkingStatus() {

  const [floorsStatus, setFloorsStatus] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [loadingMessage, setLoadingMessage] = useState('Loading Data...')
  const intervalRef = useRef(null);
  const reconnectionAttempts = useRef(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(parkingDataURL);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const data = await response.json();
        const sortedData = data.sort((a, b) => b.floor - a.floor);
        setFloorsStatus(sortedData);
        setIsLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        intervalRef.current = setInterval(() => {
          setIsLoading((prevIsLoading) => !prevIsLoading);
        }, 750);
      }
    };

    fetchData();

    return () => clearInterval(intervalRef.current);
  }, []);


  useEffect(() => {
    const connectWebSocket = () => {
      const socket = new SockJS(webSocketURL);
      const client = Stomp.over(socket);

      client.connect({}, (frame) => {
        reconnectionAttempts.current = 0;
        client.subscribe(webSocketURLtopic, (message) => {
          const receivedFloorsStatus = JSON.parse(message.body);
          const sortedData = receivedFloorsStatus.sort((a, b) => b.floor - a.floor);
          setFloorsStatus(sortedData);
          setIsLoading(false);
          clearInterval(intervalRef.current);
        });
      }, (error) => {
        console.error('Failed to connect to WebSocket and STOMP:', error);
        setFloorsStatus([]);
        setIsLoading(true);
        clearInterval(intervalRef.current);
        intervalRef.current = setInterval(() => {
          setIsLoading((prevIsLoading) => !prevIsLoading);
        }, 750);
        if (reconnectionAttempts.current < 5) {
          setTimeout(connectWebSocket, 3000);
          reconnectionAttempts.current += 1;
        } else {
          console.error('Exceeded maximum reconnection attempts');
          clearInterval(intervalRef.current);
          setLoadingMessage('Unable to connect to server.');
        }
      });

      return () => {
        client.disconnect();
        console.log('Disconnected from WebSocket.');
        clearInterval(intervalRef.current);
      };
    };

    connectWebSocket();

    return () => clearInterval(intervalRef.current);

  }, []);

  return (
    <div className={styles['parking-status-content']}>
      <div className={styles['list-container']}>
        <ul className={styles['content-list']}>
          {isLoading ? loadingMessage : floorsStatus.map((floorData, index) => (
            <li key={index}>
              <StatusPart floor={floorData.floor} freePlaces={floorData.freePlacesOnFloor} />
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}