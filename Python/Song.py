__author__ = 'tbowling3'

class Song():

    def __init__(self, s_ID, s_Title, a_ID, a_name):
        self.songID = s_ID
        self.songTitle = s_Title
        self.artistID = a_ID
        self.artistName = a_name
        self.terms = []
        self.termCount = 0

    def addTerm(self, term):
        self.terms.append(term)
        self.termCount += 1
