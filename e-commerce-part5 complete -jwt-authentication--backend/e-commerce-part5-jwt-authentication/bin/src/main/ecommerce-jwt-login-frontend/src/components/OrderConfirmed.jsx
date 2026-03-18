import React from "react";
import { Modal, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
const OrderConfirmation = ({ show, handleClose, errorMessage,orderValue }) => {
const navigate = useNavigate()
const handlechange=()=>{
  navigate("/")
  handleClose()
}
  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Order Confirmation</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {errorMessage ? (
          <div className="error-message">
            <p>{errorMessage}</p>
          </div>
        ) : (
          <p>Your order for <span className="order-value">{orderValue} </span>has been confirmed!</p>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handlechange}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default OrderConfirmation;
