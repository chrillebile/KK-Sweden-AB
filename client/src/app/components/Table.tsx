import * as React from 'react';
import TableRow from 'Components/TableRow';
import PalletInterface from 'Models/PalletModel';

interface Props {
  data: PalletInterface[];
}
interface State {
  count: number;
}

export class Table extends React.Component<Props, State> {
  render() {
    const { data } = this.props;
    return (
      <table>
        {data.map(row => {
          return (
            <TableRow key={row.id} row={row} />
          );
        })}
      </table>
    );
  }
}

export default Table;
