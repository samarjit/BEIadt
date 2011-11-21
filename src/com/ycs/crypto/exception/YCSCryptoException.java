package com.ycs.crypto.exception;

public class YCSCryptoException extends Exception
{
  private static final long serialVersionUID = 100L;

  public YCSCryptoException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public YCSCryptoException(String message)
  {
    super(message);
  }

  public YCSCryptoException(Throwable cause)
  {
    super(cause);
  }

  public YCSCryptoException()
  {
  }
}