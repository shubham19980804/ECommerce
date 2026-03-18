import React from 'react';
import { Modal, Button } from 'react-bootstrap';

const CheckoutPopup = ({ show, handleClose, cartItems, totalPrice, handleCheckout }) => {
  return (
    <div className="checkoutPopup">
   
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton className='modal-header' >
        <Modal.Title>Checkout</Modal.Title>
      </Modal.Header>
      <Modal.Body className='modal-body'>
        <div className="checkout-items">
          {cartItems.map((item) => (
            <div key={item.id} className="checkout-item" style={{ display: 'flex', marginBottom: '10px' }}>
              <img src={item.imageUrl} alt={item.name} style={{ width: '200px', marginRight: '10px' }} />
              <div>
                <b><p>{item.name}</p></b>
                <p>Quantity: {item.quantity}</p>
                <p>Price: ${item.price * item.quantity}</p>
              </div>
            </div>
          ))}
          <div className="total" >
            <h5>Total: ${totalPrice}</h5>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer className='modal-footer'>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary" onClick={handleCheckout}>
          Confirm Purchase
        </Button>
      </Modal.Footer>
    </Modal>
    </div>
  );
};

export default CheckoutPopup;
