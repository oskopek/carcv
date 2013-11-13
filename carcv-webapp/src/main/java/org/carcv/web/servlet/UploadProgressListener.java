/*
 * Copyright 2013 CarCV Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carcv.web.servlet;

import org.apache.commons.fileupload.ProgressListener;

/**
 * TODO 1 remake
 */
class UploadProgressListener implements ProgressListener {

    private long num100Ks = 0;

    private long theBytesRead = 0;
    private long theContentLength = -1;
    private int whichItem = 0;
    private int percentDone = 0;
    private boolean contentLengthKnown = false;

    /*
     * (non-Javadoc)
     *
     * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
     */
    @Override
    public void update(long bytesRead, long contentLength, int items) {

        if (contentLength > -1) {
            contentLengthKnown = true;
        }
        theBytesRead = bytesRead;
        theContentLength = contentLength;
        whichItem = items;

        long nowNum100Ks = bytesRead / 100000;
        // Only run this code once every 100K
        if (nowNum100Ks > num100Ks) {
            num100Ks = nowNum100Ks;
            if (contentLengthKnown) {
                percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
            }
            System.out.println(getMessage());
        }
    }

    public String getMessage() {
        if (theContentLength == -1) {
            return "" + theBytesRead + " of Unknown-Total bytes have been read.";
        } else {
            return "" + theBytesRead + " of " + theContentLength + " bytes have been read (" + percentDone + "% done).";
        }

    }

    public long getNum100Ks() {
        return num100Ks;
    }

    public void setNum100Ks(long num100Ks) {
        this.num100Ks = num100Ks;
    }

    public long getTheBytesRead() {
        return theBytesRead;
    }

    public void setTheBytesRead(long theBytesRead) {
        this.theBytesRead = theBytesRead;
    }

    public long getTheContentLength() {
        return theContentLength;
    }

    public void setTheContentLength(long theContentLength) {
        this.theContentLength = theContentLength;
    }

    public int getWhichItem() {
        return whichItem;
    }

    public void setWhichItem(int whichItem) {
        this.whichItem = whichItem;
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        this.percentDone = percentDone;
    }

    public boolean isContentLengthKnown() {
        return contentLengthKnown;
    }

    public void setContentLengthKnown(boolean contentLengthKnown) {
        this.contentLengthKnown = contentLengthKnown;
    }
}