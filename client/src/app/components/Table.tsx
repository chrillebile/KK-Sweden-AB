import * as React from 'react';
import PalletInterface from 'Models/PalletModel';

interface Props {
  data: any;
}

export class Table extends React.Component<Props> {
  render() {
    const pallets: Array<PalletInterface> = this.props.data;
    return (
      <table>
        <thead>
        <tr>
          <th>Id</th>
          <th>Amount</th>
          <th>ProductionDate</th>
          <th>Location</th>
          <th>DeliveryTime</th>
          <th>Blocked</th>
        </tr>
        </thead>
        <tbody>
        {pallets.map(row => {
          return (
            <tr key={row.id}>
              <td>{row.id}</td>
              <td>{row.amount}</td>
              <td>{row.productionDate}</td>
              <td>{row.location}</td>
              <td>{row.deliveryTime}</td>
              <td>{row.blocked}</td>
            </tr>
          );
        })}
        </tbody>
      </table>
    );
  }
}

export default Table;
